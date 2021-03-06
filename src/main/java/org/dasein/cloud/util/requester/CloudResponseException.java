/**
 * Copyright (C) 2009-2014 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.util.requester;

import org.apache.http.client.ClientProtocolException;
import org.dasein.cloud.CloudErrorType;

/**
 * Created by Vlad_Munthiu on 11/20/2014.
 */
public class CloudResponseException extends ClientProtocolException {
    private CloudErrorType errorType;
    private int            httpCode;
    private String         providerCode;

    public CloudResponseException(CloudErrorType cloudErrorType, int httpCode, String providerCode, String message){
        super(message);
        this.errorType = cloudErrorType;
        this.httpCode = httpCode;
        this.providerCode = providerCode;
    }

    public CloudErrorType getErrorType() {
        return errorType;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getProviderCode() {
        return providerCode;
    }
}
