from locust import HttpUser, task, between
import random

class UsuarioSimulado(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.pedido_ids = []
        # Criar um pedido inicial para ter algo para cancelar
        novo_pedido = {
            "valorTotal": round(random.uniform(10.0, 1000.0), 2),
            "numeroCartao": "4111111111111111",
            "nomeProduto": "Produto Inicial",
            "status": "FINALIZADO"
        }
        with self.client.post("/pedido/cadastrar", json=novo_pedido, catch_response=True) as response:
            if response.status_code == 201:
                pedido_criado = response.json()
                if "id" in pedido_criado:
                    self.pedido_ids.append(pedido_criado["id"])
            else:
                response.failure(f"Falha ao criar pedido inicial: {response.text}")

    @task(5)
    def criar_pedido(self):
        novo_pedido = {
            "valorTotal": round(random.uniform(10.0, 1000.0), 2),
            "numeroCartao": "1111",
            "nomeProduto": random.choice(["Teclado Mecânico", "A", "B"]),
            "status": "FINALIZADO"
        }
        with self.client.post("/pedido/cadastrar", json=novo_pedido, catch_response=True) as response:
            if response.status_code == 201:
                pedido_criado = response.json()
                if "id" in pedido_criado:
                    self.pedido_ids.append(pedido_criado["id"])
            else:
                response.failure(f"Falha ao criar pedido: {response.text}")

# @task(2)
# def buscar_pedido(self):
#     if self.pedido_ids:
#         pedido_id = random.choice(self.pedido_ids)
#         self.client.get(f"/pedido/{pedido_id}")
#     else:
#         self.client.get("/pedido/1")

# @task(2)
# def cancelar_pedido(self):
#     if self.pedido_ids:
#         pedido_id = self.pedido_ids.pop(0)
#         with self.client.delete(f"/pedido/cancelar/{pedido_id}", catch_response=True) as response:
#             if response.status_code == 204:
#                 response.success()
#             else:
#                 response.failure(f"Erro ao cancelar pedido {pedido_id}: {response.status_code} - {response.text}")
#     else:
#         pass

