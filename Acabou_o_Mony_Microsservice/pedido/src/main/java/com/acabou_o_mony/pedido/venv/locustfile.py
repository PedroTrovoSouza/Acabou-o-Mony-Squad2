from locust import HttpUser, task, between
import random


class UsuarioSimulado(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.pedido_ids = [4, 5, 6, 7, 8]
        self.cliente_ids = [1, 2]
        self.produtos = ["PA"]
        self.cartao_numero = "1234567812345678"
        self.cartao_id = [1, 2]

    def gerar_dados_pedido(self):
         return {
            "email": "trovoteste@teste.com.br",
             "pedido": {
                 "valor": round(random.uniform(100.0, 1000.0), 2),
                 "cartao": random.choice(self.cartao_id)
        },
        "clienteRemetenteId": random.choice(self.cliente_ids),
        "clienteDestinatarioId": random.choice(self.cliente_ids)
    }


    @task(1)
    def criar_pedido(self):
        nome_produto = random.choice(self.produtos)
        payload = self.gerar_dados_pedido()

        with self.client.post(f"/pedido/cadastrar?nomeProduto={nome_produto}",
                              json=payload,
                              catch_response=True) as response:
            if response.status_code == 201:
                try:
                    pedido = response.json()
                    if "id" in pedido:
                        self.pedido_ids.append(pedido["id"])
                    response.success()
                except Exception:
                    response.failure("Erro ao processar JSON da resposta.")
            else:
                response.failure(f"Erro ao criar pedido: {response.status_code} - {response.text}")

    @task(2)
    def buscar_pedido(self):
        if self.pedido_ids:
            pedido_id = random.choice(self.pedido_ids)
            with self.client.get(f"/pedido/{pedido_id}", catch_response=True) as response:
                if response.status_code == 200:
                    response.success()
                else:
                    response.failure(f"Erro ao buscar pedido {pedido_id}: {response.status_code}")
        else:
            self.client.get("/pedido/1")

    @task(1)
    def cancelar_pedido(self):
        if self.pedido_ids:
            pedido_id = self.pedido_ids.pop(0)
            with self.client.delete(f"/pedido/cancelar/{pedido_id}", catch_response=True) as response:
                if response.status_code == 204:
                    response.success()
                else:
                    response.failure(f"Erro ao cancelar pedido {pedido_id}: {response.status_code}")
