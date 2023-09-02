from fastapi import FastAPI
import uvicorn
from controller import FornecedorController
from repository import FornecedorRepository

app = FastAPI()
app.include_router(router=FornecedorController.router, prefix="/fornecedor")

if __name__ == "__main__":
    uvicorn.run("Main:app", host="0.0.0.0", port=8080, reload=True)
    FornecedorRepository.createDataBase()