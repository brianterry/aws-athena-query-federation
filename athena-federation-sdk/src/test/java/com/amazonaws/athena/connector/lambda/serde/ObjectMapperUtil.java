package com.amazonaws.athena.connector.lambda.serde;

/*-
 * #%L
 * Amazon Athena Query Federation SDK
 * %%
 * Copyright (C) 2019 Amazon Web Services
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.amazonaws.athena.connector.lambda.data.BlockAllocatorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ObjectMapperUtil
{
    private ObjectMapperUtil() {}

    public static <T> void assertSerialization(Object object, Class<T> clazz)
    {
        Object actual = null;
        try (BlockAllocatorImpl allocator = new BlockAllocatorImpl()) {
            ObjectMapper mapper = ObjectMapperFactory.create(allocator);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            mapper.writeValue(out, object);
            actual = mapper.readValue(new ByteArrayInputStream(out.toByteArray()), clazz);
            assertEquals(object, actual);
        }
        catch (IOException | AssertionError ex) {
            throw new RuntimeException(ex);
        }
    }
}
