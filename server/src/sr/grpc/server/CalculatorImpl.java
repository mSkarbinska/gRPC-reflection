package sr.grpc.server;

import sr.grpc.gen.ArithmeticOpResult;
import sr.grpc.gen.Calculation;
import sr.grpc.gen.CalculationHistory;
import sr.grpc.gen.CalculatorGrpc.CalculatorImplBase;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class CalculatorImpl extends CalculatorImplBase {
	private final ArrayList<Calculation> calculations = new ArrayList<Calculation>();

	@Override
	public void add(sr.grpc.gen.ArithmeticOpArguments request,
					io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
		System.out.println("addRequest (" + request.getArg1() + ", " + request.getArg2() +")");
		int val = request.getArg1() + request.getArg2();
		ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
		Calculation calculation = Calculation.newBuilder().setArgs(request).setOperation("addition").setResult(result).build();
		calculations.add(calculation);
		if(request.getArg1() > 100 && request.getArg2() > 100) try { Thread.sleep(5000); } catch(java.lang.InterruptedException ex) { }
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

	@Override
	public void subtract(sr.grpc.gen.ArithmeticOpArguments request,
						 io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
		System.out.println("subtractRequest (" + request.getArg1() + ", " + request.getArg2() +")");
		int val = request.getArg1() - request.getArg2();
		ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
		Calculation calculation = Calculation.newBuilder().setArgs(request).setOperation("subtraction").setResult(result).build();
		calculations.add(calculation);
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

	@Override
	public void multiply(sr.grpc.gen.ArithmeticOpArguments request,
              io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver){
		System.out.println("multiplyRequest (" + request.getArg1() + ", " + request.getArg2() +")");
		int val = request.getArg1() * request.getArg2();
		ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
		Calculation calculation = Calculation.newBuilder().setArgs(request).setOperation("multiplication").setResult(result).build();
		calculations.add(calculation);
		if(request.getArg1() > 100 && request.getArg2() > 100) try { Thread.sleep(5000); } catch(java.lang.InterruptedException ex) { }
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}
	@Override
	public void getCalculationHistory(sr.grpc.gen.GetCalculationsHistoryArguments request,
									  io.grpc.stub.StreamObserver<sr.grpc.gen.CalculationHistory> responseObserver) {
		System.out.println("getCalculationHistoryRequest (" + request.getLimit() + ")");
		CalculationHistory result = CalculationHistory.newBuilder().addAllCalculations(calculations.stream().limit(request.getLimit()).collect(Collectors.toList())).build();
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}
}
