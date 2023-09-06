from fastapi import FastAPI
import uvicorn
from controller import relatorio_controller

app = FastAPI()
app.include_router(router=relatorio_controller.router, prefix="/relatorio")

if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8084, reload=True)