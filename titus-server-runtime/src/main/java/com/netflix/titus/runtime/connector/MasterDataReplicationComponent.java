/*
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.titus.runtime.connector;

import com.netflix.titus.api.agent.service.ReadOnlyAgentOperations;
import com.netflix.titus.api.eviction.service.ReadOnlyEvictionOperations;
import com.netflix.titus.api.jobmanager.service.ReadOnlyJobOperations;
import com.netflix.titus.common.runtime.TitusRuntime;
import com.netflix.titus.runtime.connector.agent.AgentDataReplicator;
import com.netflix.titus.runtime.connector.agent.AgentManagementClient;
import com.netflix.titus.runtime.connector.agent.CachedReadOnlyAgentOperations;
import com.netflix.titus.runtime.connector.agent.replicator.AgentDataReplicatorProvider;
import com.netflix.titus.runtime.connector.eviction.CachedReadOnlyEvictionOperations;
import com.netflix.titus.runtime.connector.eviction.EvictionDataReplicator;
import com.netflix.titus.runtime.connector.eviction.EvictionServiceClient;
import com.netflix.titus.runtime.connector.eviction.replicator.EvictionDataReplicatorProvider;
import com.netflix.titus.runtime.connector.jobmanager.CachedReadOnlyJobOperations;
import com.netflix.titus.runtime.connector.jobmanager.JobDataReplicator;
import com.netflix.titus.runtime.connector.jobmanager.JobManagementClient;
import com.netflix.titus.runtime.connector.jobmanager.replicator.JobDataReplicatorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterDataReplicationComponent {

    @Bean
    public AgentDataReplicator getAgentDataReplicator(AgentManagementClient client, TitusRuntime titusRuntime) {
        return new AgentDataReplicatorProvider(client, titusRuntime).get();
    }

    @Bean
    public ReadOnlyAgentOperations getReadOnlyAgentOperations(AgentDataReplicator replicator) {
        return new CachedReadOnlyAgentOperations(replicator);
    }

    @Bean
    public JobDataReplicator getJobDataReplicator(JobManagementClient client, TitusRuntime titusRuntime) {
        return new JobDataReplicatorProvider(client, titusRuntime).get();
    }

    @Bean
    public ReadOnlyJobOperations getReadOnlyJobOperations(JobDataReplicator replicator) {
        return new CachedReadOnlyJobOperations(replicator);
    }

    @Bean
    public EvictionDataReplicator getEvictionDataReplicator(EvictionServiceClient client, TitusRuntime titusRuntime) {
        return new EvictionDataReplicatorProvider(client, titusRuntime).get();
    }

    @Bean
    public ReadOnlyEvictionOperations getReadOnlyEvictionOperations(EvictionDataReplicator replicator) {
        return new CachedReadOnlyEvictionOperations(replicator);
    }
}
