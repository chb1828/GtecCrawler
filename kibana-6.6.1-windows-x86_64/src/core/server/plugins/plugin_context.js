"use strict";
/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Object.defineProperty(exports, "__esModule", { value: true });
/**
 * This returns a facade for `CoreContext` that will be exposed to the plugin initializer.
 * This facade should be safe to use across entire plugin lifespan.
 *
 * This is called for each plugin when it's created, so each plugin gets its own
 * version of these values.
 *
 * We should aim to be restrictive and specific in the APIs that we expose.
 *
 * @param coreContext Kibana core context
 * @param pluginManifest The manifest of the plugin we're building these values for.
 * @internal
 */
function createPluginInitializerContext(coreContext, pluginManifest) {
    return {
        /**
         * Environment information that is safe to expose to plugins and may be beneficial for them.
         */
        env: { mode: coreContext.env.mode },
        /**
         * Plugin-scoped logger
         */
        logger: {
            get(...contextParts) {
                return coreContext.logger.get('plugins', pluginManifest.id, ...contextParts);
            },
        },
        /**
         * Core configuration functionality, enables fetching a subset of the config.
         */
        config: {
            /**
             * Reads the subset of the config at the `configPath` defined in the plugin
             * manifest and validates it against the schema in the static `schema` on
             * the given `ConfigClass`.
             * @param ConfigClass A class (not an instance of a class) that contains a
             * static `schema` that we validate the config at the given `path` against.
             */
            create(ConfigClass) {
                return coreContext.configService.atPath(pluginManifest.configPath, ConfigClass);
            },
            createIfExists(ConfigClass) {
                return coreContext.configService.optionalAtPath(pluginManifest.configPath, ConfigClass);
            },
        },
    };
}
exports.createPluginInitializerContext = createPluginInitializerContext;
/**
 * This returns a facade for `CoreContext` that will be exposed to the plugin `start` method.
 * This facade should be safe to use only within `start` itself.
 *
 * This is called for each plugin when it's started, so each plugin gets its own
 * version of these values.
 *
 * We should aim to be restrictive and specific in the APIs that we expose.
 *
 * @param coreContext Kibana core context
 * @param plugin The plugin we're building these values for.
 * @internal
 */
function createPluginStartContext(coreContext, plugin) {
    return {};
}
exports.createPluginStartContext = createPluginStartContext;
