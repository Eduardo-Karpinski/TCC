from fastapi import FastAPI
import uvicorn
from controller import produto_controller
from database import database

app = FastAPI()
app.include_router(router=produto_controller.router, prefix="/produto")

if __name__ == "__main__":
    database.create_data_base()
    uvicorn.run("main:app", host="0.0.0.0", port=8081, reload=False)