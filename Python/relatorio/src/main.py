from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
import uvicorn
from controller import relatorio_controller

app = FastAPI()
app.include_router(router=relatorio_controller.router, prefix="/relatorio")
app.mount("/static", StaticFiles(directory="static"), name="static")

if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8084, reload=False)