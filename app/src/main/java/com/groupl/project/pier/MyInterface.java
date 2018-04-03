package com.groupl.project.pier;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/**
 * Created by alexandra on 13/03/2018.
 */

public interface MyInterface {
    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    ResponseClass lambdaBackendNodeJsTutorial(RequestClass request);
}
