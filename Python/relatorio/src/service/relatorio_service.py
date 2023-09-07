from datetime import datetime
import requests
from decimal import Decimal
import locale
from fastapi.templating import Jinja2Templates
from fastapi import Request
import json

locale.setlocale(locale.LC_ALL, 'pt_BR.UTF-8')
templates = Jinja2Templates(directory="templates")

def getRelatorio(request: Request, data1: datetime, data2: datetime):

	result = {}
	vendas_request = requests.get(f"http://localhost:8083/venda/{data1}/{data2}").json()
	estoques_request = requests.get("http://localhost:8082/estoque/estoqueBaixo").json()
	produtos_request = requests.get("http://localhost:8081/produto/valorBaixo").json()
 
	if vendas_request:
		result['totalDeVendas'] = len(vendas_request)
		valorVendido = Decimal(0)
		quantidadeProdutosVendidos = Decimal(0)

		for venda in vendas_request:
			produtos = venda['produtos']
			for produto in produtos:
				valorVendido += Decimal(produto['preco']) * Decimal(produto['quantidade'])
				quantidadeProdutosVendidos += Decimal(produto['quantidade'])

		result['valorVendido'] = locale.currency(valorVendido, grouping=True)
		result['quantidadeDeProdutosVendidos'] = quantidadeProdutosVendidos
    
	if estoques_request:
		estoques_baixos = []
		for estoque in estoques_request:
			produto = estoque["produto"]
			estoque_atual = {
				"produto": f"{produto['descricao']} ({produto['ean']})",
				"quantidade": estoque["quantidade"],
				"quantidadeMinima": estoque["quantidade_minima"]
        	}
			estoques_baixos.append(estoque_atual)
		result['estoquesBaixos'] = estoques_baixos
  
	if produtos_request:
		produtos_com_preco_baixo = []
		for produto in produtos_request:
			produto_atual = {
				"produto": f"{produto['descricao']} ({produto['ean']})",
				"preco": locale.currency(Decimal(produto['preco'])),
				"custo": locale.currency(Decimal(produto['custo']))
			}
			produtos_com_preco_baixo.append(produto_atual)
		result['produtosComPrecoBaixo'] = produtos_com_preco_baixo

	return templates.TemplateResponse("relatorio.html", {"request": request, "data": json.dumps(result, default=decimal_serializer)})

def decimal_serializer(obj):
    if isinstance(obj, Decimal):
        return str(obj)
    raise TypeError("Object of type Decimal is not JSON serializable")