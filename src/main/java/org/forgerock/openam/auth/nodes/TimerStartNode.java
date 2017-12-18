/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2017 ForgeRock AS.
 */

package org.forgerock.openam.auth.nodes;

import javax.inject.Inject;

import org.forgerock.json.JsonValue;
import org.forgerock.openam.annotations.sm.Attribute;
import org.forgerock.openam.auth.node.api.Action;
import org.forgerock.openam.auth.node.api.Node;
import org.forgerock.openam.auth.node.api.NodeProcessException;
import org.forgerock.openam.auth.node.api.SingleOutcomeNode;
import org.forgerock.openam.auth.node.api.TreeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;

/**
 * A node which records the current time to a property within the authentication shared state.
 *
 * This node should be used with {@link TimerStopNode}.
 */
@Node.Metadata(outcomeProvider = SingleOutcomeNode.OutcomeProvider.class,
        configClass = TimerStartNode.Config.class)
public class TimerStartNode extends SingleOutcomeNode {

    static final String DEFAULT_START_TIME_PROPERTY = "TimerNodeStartTime";

    private final Logger logger = LoggerFactory.getLogger("amAuth");
    private final Config config;

    /**
     * Constructs a new {@link TimerStartNode} instance.
     *
     * @param config
     *          Node configuration.
     */
    @Inject
    public TimerStartNode(@Assisted TimerStartNode.Config config) throws NodeProcessException {
        this.config = config;
    }

    @Override
    public Action process(TreeContext context) {
        long startTimeMillis = System.currentTimeMillis();
        logger.debug("{} recording start time {} in {}",
                TimerStartNode.class.getSimpleName(), startTimeMillis, config.startTimeProperty());
        JsonValue newSharedState = context.sharedState.copy();
        newSharedState.put(config.startTimeProperty(), startTimeMillis);
        return goToNext().replaceSharedState(newSharedState).build();
    }

    /**
     * Configuration for the node.
     */
    public interface Config {

        /**
         * Identifier of property into which start time should be stored.
         *
         * Defaults to {@literal "TimerNodeStartTime"}
         *
         * @return property name.
         */
        @Attribute(order = 100)
        default String startTimeProperty() {
            return DEFAULT_START_TIME_PROPERTY;
        }
    }
}

