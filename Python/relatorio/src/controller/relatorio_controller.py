from fastapi import APIRouter, Request
from datetime import datetime
from service import relatorio_service
from fastapi.responses import HTMLResponse

router = APIRouter()

@router.get("/{data1}/{data2}", response_class=HTMLResponse)
def getRelatorio(request: Request, data1: datetime, data2: datetime):
    return relatorio_service.getRelatorio(request, data1, data2)