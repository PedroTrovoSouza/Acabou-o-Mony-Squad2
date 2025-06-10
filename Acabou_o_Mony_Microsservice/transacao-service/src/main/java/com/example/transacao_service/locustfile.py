from locust import HttpUser, task, between
import random
import datetime
import json

class TransacaoSimulada(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.transacoes_ids = []

    def gerar_transacao(self):
        tipo_transacao = random.choice(["DEBITO", "CREDITO"])
        valor = round(random.uniform(10.0, 1000.0), 2)

        ids_validos = [2, 5, 6, 7]

        cliente_remetente = random.choice(ids_validos)
        cliente_destinatario = random.choice(ids_validos)

        # Evitar que sejam iguais
        while cliente_destinatario == cliente_remetente:
            cliente_destinatario = random.choice(ids_validos)

        return {
            "tipoTransacao": tipo_transacao,
            "valor": valor,
            "dthora": datetime.datetime.now().isoformat(timespec='seconds'),
            "clienteDestinatarioId": cliente_destinatario,
            "clienteRemetenteId": cliente_remetente,
            "cartaoId": random.randint(1, 2)
        }

    @task(2)
    def cadastrar_transacao(self):
        payload = self.gerar_transacao()

        with self.client.post("/transacao", json=payload,
                              headers={"Content-Type": "application/json"},
                              catch_response=True) as response:
            if response.status_code == 201:
                try:
                    transacao = response.json()
                    id_novo = transacao.get("id")
                    if id_novo:
                        self.transacoes_ids.append(id_novo)
                    response.success()
                except Exception as e:
                    response.failure(f"Erro ao interpretar resposta: {e}")
            elif response.status_code == 400:
                response.failure(f"Transação inválida: {response.text}")
            else:
                response.failure(f"Erro inesperado: {response.status_code} - {response.text}")

    @task(1)
    def buscar_transacao_por_id(self):
        if not self.transacoes_ids:
            return
        transacao_id = random.choice(self.transacoes_ids)
        with self.client.get(f"/transacao/{transacao_id}", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 404:
                response.failure(f"Transação com ID {transacao_id} não encontrada.")
            else:
                response.failure(f"Erro ao buscar transação por ID: {response.status_code} - {response.text}")

    @task(1)
    def listar_todas_transacoes(self):
        with self.client.get("/transacao", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 204:
                response.failure("Nenhuma transação encontrada (204 No Content).")
            else:
                response.failure(f"Erro ao listar transações: {response.status_code} - {response.text}")
