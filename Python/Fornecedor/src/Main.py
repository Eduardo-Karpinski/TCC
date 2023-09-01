from fastapi import FastAPI
from controller import FornecedorController
from repository import FornecedorRepository

app = FastAPI()
app.include_router(FornecedorController.router)

FornecedorRepository.createDataBase()

# python -m uvicorn Main:app --reload --port 8080