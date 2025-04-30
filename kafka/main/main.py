from kafka import KafkaConsumer, KafkaProducer
import json
import time

# Kafka consumer (partição fibo-1)
consumer = KafkaConsumer(
    'fibo-1',
    bootstrap_servers='localhost:9093',
    group_id='fibo-group',
    auto_offset_reset='latest',
    enable_auto_commit=True,
    value_deserializer=lambda m: int(m.decode('utf-8'))
)

# Kafka producer (resposta na partição fibo-2)
producer = KafkaProducer(
    bootstrap_servers='localhost:9093',
    value_serializer=lambda v: str(v).encode('utf-8')
)

print("Consumidor ativo. Esperando mensagens...")


while True:
    for message in consumer:
        # Processa a mensagem recebida
        n = message.value

        # check if is number
        if not isinstance(n, int):
            print("Mensagem não é um número inteiro. Ignorando.")
            continue

        # Calcula o número de Fibonacci
        if n == 0:
            fibo = 0
        elif n == 1:
            fibo = 1
        else:
            a, b = 0, 1
            for _ in range(2, n + 1):
                a, b = b, a + b
            fibo = b

        # Envia o resultado para a partição fibo-2
        producer.send('fibo-2', value=fibo)
        print(f"Resultado enviado: {fibo}")
    time.sleep(1)  # Espera um pouco e tenta de novo