from fastapi import FastAPI
import uvicorn
from controller import estoque_controller
from database import database

app = FastAPI()
app.include_router(router=estoque_controller.router, prefix="/venda")

if __name__ == "__main__":
    database.create_data_base()
    uvicorn.run("main:app", host="0.0.0.0", port=8083, reload=True)