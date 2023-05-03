import grpc
from yagrc import reflector as yagrc_reflector


reflector = yagrc_reflector.GrpcReflectionClient()
target = "127.0.0.5:50051"
with grpc.insecure_channel(target) as channel:
    reflector.load_protocols(channel, symbols=["calculator.Calculator"])
    stub_class = reflector.service_stub_class("calculator.Calculator")
    request_class = reflector.message_class("calculator.ArithmeticOpArguments")
    stub = stub_class(channel)

    response = stub.Add(request_class(arg1=5, arg2=1))
    print(response)

    response = stub.Multiply(request_class(arg1=2, arg2=5))
    print(response)

    response = stub.Subtract(request_class(arg1=2, arg2=5))
    print(response)

    request_class = reflector.message_class("calculator.GetCalculationsHistoryArguments")
    response = stub.GetCalculationHistory(request_class(limit=3))
    print(response)

    reflector.load_protocols(channel, symbols=["calculator.AdvancedCalculator"])
    stub_class = reflector.service_stub_class("calculator.AdvancedCalculator")
    request_class = reflector.message_class("calculator.ComplexArithmeticOpArguments")
    enum_class = reflector.enum_class("calculator.OperationType")

    stub = stub_class(channel)
    response = stub.ComplexOperation(request_class(optype=enum_class.Value("AVG"), args=[1, 2, 3, 4, 5]))
    print(response)


