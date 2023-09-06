from fastapi import APIRouter
from datetime import datetime
from service import relatorio_service

router = APIRouter()

@router.get("/{data1}/{data2}")
def getRelatorio(data1: datetime, data2: datetime):
    return relatorio_service.getRelatorio(data1, data2)