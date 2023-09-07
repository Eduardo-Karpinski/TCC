from fastapi import FastAPI
import uvicorn
from controller import fornecedor_controller
from database import database

app = FastAPI()
app.include_router(router=fornecedor_controller.router, prefix="/fornecedor")

if __name__ == "__main__":
    database.create_data_base()
    uvicorn.run("main:app", host="0.0.0.0", port=8080, reload=False)