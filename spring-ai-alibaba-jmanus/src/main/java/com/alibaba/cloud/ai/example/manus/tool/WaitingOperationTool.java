/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.example.manus.tool;

import com.alibaba.cloud.ai.example.manus.tool.code.ToolExecuteResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.ai.chat.model.ToolContext;



/**
 * 当执行到该工具时，说明要等到用户进行操作，需结束当前步骤，等到用户操作完毕并输入指令后进行之后的步骤
 *
 * @author azir
 * @since 2025/06/01
 */
public class WaitingOperationTool implements ToolCallBiFunctionDef {

    public static final String NAME = "waiting_user_operation";

    @Override
    public String getServiceGroup() {
        return "default-service-group";
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return """
                Waiting for the user to take operation..
                For example:
                -The current browser page requires login
                -Check if the generated document is correct
                -Encountering an unconfirmed input box
                
                The waiting operation message returned to the user should include:
                -Operations already performed
                -The problems currently encountered
                -How should users handle it
                
                Finally, it is necessary to remind the user that they need to reply after the operation is successful before proceeding to the next step
                """ ;
    }

    @Override
    public String getParameters() {
        return """
                {
                	"type": "object",
                	"properties": {
                		"message": {
                			"type": "string",
                			"description": "The waiting operation message"
                		}
                	},
                	"required": [
                		"message"
                	]
                }
                """;
    }

    @Override
    public Class<?> getInputType() {
        return String.class;
    }

    @Override
    public boolean isReturnDirect() {
        return true;
    }

    @Override
    public void setPlanId(String planId) {
    }

    @Override
    public String getCurrentToolStateString() {
        return "";
    }

    @Override
    public void cleanup(String planId) {

    }

    /**
     * 会删除未执行的阶段
     * @param s the first function argument
     * @param toolContext the second function argument
     * @return
     */
    @Override
    public ToolExecuteResult apply(String s, ToolContext toolContext) {
        JSONObject response = JSON.parseObject(s);
        String message = response.getString("message");
        return new ToolExecuteResult(message, true);
    }
}
