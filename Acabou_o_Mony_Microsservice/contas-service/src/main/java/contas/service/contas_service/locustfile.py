from locust import HttpUser, task, between
import random
import string
from datetime import datetime, timedelta
from decimal import Decimal

# Testes para cliente

def random_cpf():
    return ''.join(random.choices(string.digits, k=11))

def random_cnpj():
    return ''.join(random.choices(string.digits, k=14))

def random_nome():
    nomes = ["Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel"]
    sobrenomes = ["Silva", "Souza", "Oliveira", "Pereira", "Lima", "Costa"]
    return f"{random.choice(nomes)} {random.choice(sobrenomes)}"

def random_nome_empresa():
    empresas = ["Tech", "Global", "Agro", "Solutions", "Group", "Consult", "Data"]
    sufixos = ["LTDA", "S.A.", "ME", "EIRELI"]
    return f"{random.choice(empresas)} {random.choice(sufixos)}"

def random_email(nome):
    dominios = ["teste.com", "email.com", "mail.com"]
    nome_email = nome.lower().replace(" ", ".")
    return f"{nome_email}@{random.choice(dominios)}"

def random_data_nascimento():
    inicio = datetime.now() - timedelta(days=365*60)
    fim = datetime.now() - timedelta(days=365*18)
    data = inicio + (fim - inicio) * random.random()
    return data.strftime("%Y-%m-%d")

def random_data_fundacao():
    inicio = datetime.now() - timedelta(days=365 * 30)
    fim = datetime.now() - timedelta(days=365 * 1)
    data = inicio + (fim - inicio) * random.random()
    return data.strftime("%Y-%m-%d")

class UsuarioSimulado(HttpUser):
    wait_time = between(1, 3)

    @task(1)
    def cadastrar_pessoa_fisica(self):
        nome = random_nome()
        cpf = random_cpf()
        perfil_economico = random.choice(["BAIXO", "MEDIO", "ALTO", "EMPRESA"])
        email = random_email(nome)
        data_nascimento = random_data_nascimento()
        genero = random.choice(["MASCULINO", "FEMININO", "PREFIRO_NAO_INFORMAR"])

        payload = {
            "nome": nome,
            "cpf": cpf,
            "perfilEconomico": perfil_economico,
            "email": email,
            "dataNascimento": data_nascimento,
            "genero": genero
        }

        with self.client.post("/clientes/pf", json=payload, catch_response=True) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f"Falha no cadastro PF: {response.status_code} - {response.text}")

    @task(1)
    def cadastrar_pessoa_juridica(self):
        nome = random_nome_empresa()
        cnpj = random_cnpj()
        perfil_economico = random.choice(["BAIXO", "MEDIO", "ALTO", "EMPRESA"])
        email = random_email(nome)
        data_fundacao = random_data_fundacao()

        payload = {
            "nome": nome,
            "cnpj": cnpj,
            "perfilEconomico": perfil_economico,
            "email": email,
            "dataFundacao": data_fundacao
        }

        with self.client.post("/clientes/pj", json=payload, catch_response=True) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f"Falha no cadastro PJ: {response.status_code} - {response.text}")

    @task(1)
    def atualizar_pessoa_fisica(self):
        id_existente = random.randint(1, 100)

        get_response = self.client.get(f"/clientes/pf/{id_existente}")
        if get_response.status_code != 200:
            return

        nome = random_nome()
        payload = {
            "nome": nome,
            "email": random_email(nome),
            "dataNascimento": random_data_nascimento(),
            "genero": random.choice(["MASCULINO", "FEMININO", "PREFIRO_NAO_INFORMAR"])
        }

        with self.client.put(f"/clientes/pf/{id_existente}", json=payload, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Falha na atualização PF: {response.status_code} - {response.text}")

    @task(1)
    def deletar_pessoa_fisica(self):
        nome = random_nome()
        cpf = random_cpf()
        perfil_economico = random.choice(["BAIXO", "MEDIO", "ALTO", "EMPRESA"])
        email = random_email(nome)
        data_nascimento = random_data_nascimento()
        genero = random.choice(["MASCULINO", "FEMININO", "PREFIRO_NAO_INFORMAR"])

        payload = {
            "nome": nome,
            "cpf": cpf,
            "perfilEconomico": perfil_economico,
            "email": email,
            "dataNascimento": data_nascimento,
            "genero": genero
        }

        post_response = self.client.post("/clientes/pf", json=payload)
        if post_response.status_code == 201:
            with self.client.delete(f"/clientes/pf/{cpf}", catch_response=True) as response:
                if response.status_code == 200:
                    response.success()
                else:
                    response.failure(f"Falha ao deletar PF: {response.status_code} - {response.text}")

    @task(1)
    def deletar_pessoa_juridica(self):
        nome = random_nome_empresa()
        cnpj = random_cnpj()
        perfil_economico = random.choice(["BAIXO", "MEDIO", "ALTO", "EMPRESA"])
        email = random_email(nome)
        data_fundacao = random_data_fundacao()

        payload = {
            "nome": nome,
            "cnpj": cnpj,
            "perfilEconomico": perfil_economico,
            "email": email,
            "dataFundacao": data_fundacao
        }

        post_response = self.client.post("/clientes/pj", json=payload)
        if post_response.status_code == 201:
            with self.client.delete(f"/clientes/pj/{cnpj}", catch_response=True) as response:
                if response.status_code == 200:
                    response.success()
                else:
                    response.failure(f"Falha ao deletar PJ: {response.status_code} - {response.text}")

# Testes para conta

class ContaSimulada(HttpUser):
    wait_time = between(1, 3)

    def criar_cliente_pf_para_conta(self):
        nome = random_nome()
        cpf = random_cpf()
        perfil = random.choice(["BAIXO", "MEDIO", "ALTO"])
        email = random_email(nome)
        data_nasc = random_data_nascimento()
        genero = random.choice(["MASCULINO", "FEMININO", "PREFIRO_NAO_INFORMAR"])
        payload = {
            "nome": nome,
            "cpf": cpf,
            "perfilEconomico": perfil,
            "email": email,
            "dataNascimento": data_nasc,
            "genero": genero
        }
        resp = self.client.post("/clientes/pf", json=payload)
        if resp.status_code == 201:
            return resp.json().get("id")
        return None

    @task(1)
    def cadastrar_conta_pf(self):
        id_cliente = self.criar_cliente_pf_para_conta()
        if not id_cliente:
            return

        payload = {
            "idCliente": id_cliente,
            "tipoConta": random.choice(["CONTA_CORRENTE", "CONTA_POUPANCA", "CONTA_SALARIO"]),
            "tipoCliente": "PESSOA_FISICA",
            "agencia": "0001"
        }
        with self.client.post("/contas", json=payload, catch_response=True) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f"Erro ao cadastrar conta: {response.status_code} - {response.text}")

    @task(1)
    def atualizar_tipo_conta(self):
        id_conta = 2
        payload = {
            "tipoConta": random.choice(["CONTA_CORRENTE", "CONTA_POUPANCA"]),
            "agencia": "0001",
            "isDebito": True,
            "isCredito": True
        }
        with self.client.put(f"/contas/conta/{id_conta}", json=payload, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Falha ao atualizar tipo de conta: {response.status_code} - {response.text}")

    @task(1)
    def ativar_desativar_conta(self):
        id_conta = 2

        with self.client.put(f"/contas/status/{id_conta}", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Erro ao alterar status: {response.status_code} - {response.text}")


    @task(1)
    def atualizar_saldo(self):
        id_conta = 2
        valor = round(random.uniform(100.0, 1000.0), 2)
        with self.client.put(f"/contas/saldo/{id_conta}", json=valor, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Erro ao atualizar saldo: {response.status_code} - {response.text}")

    @task(1)
    def deletar_conta(self):
        id_conta = 2
        with self.client.delete(f"/contas/{id_conta}", catch_response=True) as response:
            if response.status_code in [200, 204]:
                response.success()
            else:
                response.failure(f"Erro ao deletar conta: {response.status_code} - {response.text}")

class CartaoSimulado(HttpUser):
    wait_time = between(1, 3)

    def gerar_numero_cartao(self):
        return ''.join([str(random.randint(0,9)) for _ in range(16)])

    def gerar_data_vencimento(self):
        futuro = datetime.now() + timedelta(days=365*random.randint(1,5))
        return futuro.strftime("%Y-%m-%d")

    def gerar_cvv(self):
        return f"{random.randint(100, 999)}"

    def gerar_bandeira(self):
        return random.choice(["VISA", "MASTERCARD", "ELO", "AMEX"])

    def gerar_payload(self):
        return {
            "nome": f"Cartao {random.randint(1, 9999)}",
            "numero": self.gerar_numero_cartao(),
            "vencimento": self.gerar_data_vencimento(),
            "cvv": self.gerar_cvv(),
            "bandeira": self.gerar_bandeira(),
            "isCredito": random.choice([True, False]),
            "isDebito": random.choice([True, False]),
            "limiteCredito": round(random.uniform(1000, 5000), 2),
            "contaId": random.randint(1, 20)  # ajuste conforme IDs válidos no seu banco
        }

    @task(3)
    def criar_cartao(self):
        payload = self.gerar_payload()
        with self.client.post("/cartao", json=payload, catch_response=True) as response:
            if response.status_code == 201:
                # Supondo que o ID retornado seja usado para atualizar/deletar
                data = response.json()
                self.cartao_id = data.get("id")  # guarde para atualizar/deletar
                response.success()
            else:
                response.failure(f"Falha ao criar cartão: {response.status_code}")

    @task(2)
    def atualizar_cartao(self):
        if not hasattr(self, "cartao_id") or not self.cartao_id:
            return
        payload = self.gerar_payload()
        with self.client.put(f"/cartao/{self.cartao_id}", json=payload, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Falha ao atualizar cartão: {response.status_code}")

    @task(1)
    def deletar_cartao(self):
        if not hasattr(self, "cartao_id") or not self.cartao_id:
            return
        with self.client.delete(f"/cartao/{self.cartao_id}", catch_response=True) as response:
            if response.status_code in [200, 204]:
                self.cartao_id = None  # reseta o ID depois de deletar
                response.success()
            else:
                response.failure(f"Falha ao deletar cartão: {response.status_code}")