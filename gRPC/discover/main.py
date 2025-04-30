import grpc
import compute_pb2, compute_pb2_grpc

def run():
    channel = grpc.insecure_channel('localhost:50051')
    stub = compute_pb2_grpc.ComputeStub(channel)
    n = int(input("Digite um inteiro: "))
    resp = stub.Square(compute_pb2.Number(value=n))
    print(f"{n}² = {resp.value}")

if __name__ == '__main__':
    run()