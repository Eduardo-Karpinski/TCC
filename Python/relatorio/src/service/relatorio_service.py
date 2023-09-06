from datetime import datetime
import requests

def getRelatorio(data1: datetime, data2: datetime):
    
	venda = requests.get(f"http://localhost:8083/venda/{data1}/{data2}").json()
	estoque = requests.get("http://localhost:8082/estoque/estoqueBaixo").json()
	produto = requests.get("http://localhost:8081/produto/valorBaixo").json()
    
	return None