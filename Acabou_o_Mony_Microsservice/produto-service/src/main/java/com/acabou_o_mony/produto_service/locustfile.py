from locust import HttpUser, task, between
import random
import string

class ProdutoSimulado(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.nomes_cadastrados = []

    def gerar_nome_produto(self):
        return "Produto_" + ''.join(random.choices(string.ascii_uppercase + string.digits, k=5))

    def gerar_dados_produto(self):
        nome = self.gerar_nome_produto()
        return {
            "nome": nome,
            "categoria": random.choice([
                'ALIMENTOS_BEBIDAS', 'AUTOMOTIVO', 'BRINQUEDOS_JOGOS', 'COSMETICOS_HIGIENE',
                'ELETRONICOS', 'ESPORTES_LAZER', 'FERRAMENTAS_CONSTRUCAO',
                'LIVROS_PAPELARIA', 'MOVEIS_DECORACAO', 'ROUPAS_ACESSORIOS'
            ]),
            "preco": round(random.uniform(10.0, 500.0), 2),
            "qtd_estoque": random.randint(1, 100)
        }, nome

    @task(1)
    def cadastrar_produto(self):
        payload, nome = self.gerar_dados_produto()
        print("JSON enviado:", payload)
        with self.client.post("/produto",
                              json=payload,
                              headers={"Content-Type": "application/json"},
                              catch_response=True) as response:
            if response.status_code == 201:
                self.nomes_cadastrados.append(nome)
                response.success()
            else:
                response.failure(f"Erro ao cadastrar produto: {response.status_code} - {response.text}")

    @task(2)
    def buscar_produto_por_id(self):
        produto_ids = [1]
        produto_id = random.choice(produto_ids)

        with self.client.get(f"/produto/{produto_id}", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 404:
                response.failure(f"Produto com ID {produto_id} não encontrado (404).")
            else:
                response.failure(f"Erro ao buscar produto {produto_id}: {response.status_code} - {response.text}")

    @task(3)
    def buscar_produto_por_nome(self):
        if not self.nomes_cadastrados:
            busca_nome = "Produto_RCINE"
        else:
            busca_nome = random.choice(self.nomes_cadastrados)

        with self.client.get(f"/produto/nome?nome={busca_nome}", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 404:
                response.failure(f"Nenhum produto encontrado para o nome '{busca_nome}'.")
            else:
                response.failure(f"Erro ao buscar produto por nome: {response.status_code} - {response.text}")

    @task(4)
    def atualizar_preco_produto(self):
        if not self.nomes_cadastrados:
            return

        produto_ids = [1]
        produto_id = random.choice(produto_ids)

        novo_preco = round(random.uniform(10.0, 500.0), 2)
        payload = {"preco": novo_preco}

        with self.client.patch(f"/produto/{produto_id}/preco",
                           json=payload,
                           headers={"Content-Type": "application/json"},
                           catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 404:
                response.failure(f"Produto com ID {produto_id} não encontrado para atualizar preço.")
            else:
                response.failure(f"Erro ao atualizar preço do produto {produto_id}: {response.status_code} - {response.text}")

    @task(5)
    def atualizar_estoque_produto(self):
        if not self.nomes_cadastrados:
            return

        produto_ids = [1]
        produto_id = random.choice(produto_ids)

        nova_qtd_estoque = random.randint(1, 200)
        payload = {"qtd_estoque": nova_qtd_estoque}

        with self.client.patch(f"/produto/{produto_id}/estoque",
                           json=payload,
                           headers={"Content-Type": "application/json"},
                           catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 404:
                response.failure(f"Produto com ID {produto_id} não encontrado para atualizar estoque.")
            else:
                response.failure(f"Erro ao atualizar estoque do produto {produto_id}: {response.status_code} - {response.text}")

    @task(6)
    def atualizar_categoria_produto(self):
        if not self.nomes_cadastrados:
            return

        produto_ids = [1, 2, 3, 4, 5, 6, 7]
        produto_id = random.choice(produto_ids)

        nova_categoria = random.choice([
            'ALIMENTOS_BEBIDAS', 'AUTOMOTIVO', 'BRINQUEDOS_JOGOS', 'COSMETICOS_HIGIENE',
            'ELETRONICOS', 'ESPORTES_LAZER', 'FERRAMENTAS_CONSTRUCAO',
            'LIVROS_PAPELARIA', 'MOVEIS_DECORACAO', 'ROUPAS_ACESSORIOS'
        ])
        payload = {"categoria": nova_categoria}

        with self.client.patch(f"/produto/{produto_id}/categoria",
                           json=payload,
                           headers={"Content-Type": "application/json"},
                           catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            elif response.status_code == 404:
                response.failure(f"Produto com ID {produto_id} não encontrado para atualizar categoria.")
            else:
                response.failure(f"Erro ao atualizar categoria do produto {produto_id}: {response.status_code} - {response.text}")

    @task(7)
    def atualizar_nome_produto(self):
        if not self.nomes_cadastrados:
            return  # Sem produtos cadastrados ainda

        produto_ids = [1]  # IDs válidos no banco
        produto_id = random.choice(produto_ids)

        novo_nome = "Produto_" + ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))
        payload = {"nome": novo_nome}

        with self.client.patch(f"/produto/{produto_id}/nome",
                           json=payload,
                           headers={"Content-Type": "application/json"},
                           catch_response=True) as response:
            if response.status_code == 200:
                self.nomes_cadastrados.append(novo_nome)  # Atualiza a lista de nomes cadastrados
                response.success()
            elif response.status_code == 404:
                response.failure(f"Produto com ID {produto_id} não encontrado para atualizar nome.")
            else:
                response.failure(f"Erro ao atualizar nome do produto {produto_id}: {response.status_code} - {response.text}")