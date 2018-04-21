package com.groupl.project.pier;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;


public interface MyInterface {
    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    ResponseClass lambdaBackendNodeJsTutorial(RequestClass request);
}
