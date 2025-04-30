import grpc
from kafka import KafkaConsumer, KafkaProducer
import compute_pb2, compute_pb2_grpc
import json
import time

grpc_channel = grpc.insecure_channel('localhost:50051')
grpc_stub = compute_pb2_grpc.ComputeFiboStub(grpc_channel)

consumer = KafkaConsumer(
    'fibo-1',
    bootstrap_servers='localhost:9093',
    group_id='fibo-group',
    auto_offset_reset='latest',
    enable_auto_commit=True,
    value_deserializer=lambda m: m.decode('utf-8')
)

# Producer para 'fibo-2'
producer = KafkaProducer(
    bootstrap_servers='localhost:9093',
    value_serializer=lambda v: v.encode('utf-8')
)

print("Consumidor ativo. Esperando mensagens...")


while True:
    for msg in consumer:
        try:
            payload = msg.value
            print(f"Recebido: {payload}")

            uuid, value_str = payload.strip().split(':')
            n = int(value_str)
            response = grpc_stub.Fibonacci(compute_pb2.Number(value=n))

            response = f"{uuid}:{response.value}"
            print(f"Respondendo: {response}")

            producer.send('fibo-2', value=response)
            producer.flush()
        except Exception as e:
            print(f"Erro ao processar mensagem: {e}")
    time.sleep(1)  # Espera um pouco e tenta de novo