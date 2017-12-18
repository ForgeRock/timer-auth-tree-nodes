<!--
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
-->
# Timer Auth Tree Nodes

A simple pair of authentication nodes for ForgeRock's [Identity Platform][forgerock_platform] 5.5 and above. These nodes can be configured to update a DropWizard Metrics Timer within AM which can then be read using JMX or pushed to a Graphite server.

## Installation

Copy the .jar file from the ../target directory into the ../web-container/webapps/openam/WEB-INF/lib directory where AM is deployed.  Restart the web container to pick up the new nodes.  The nodes will then appear in the authentication trees components palette.

## Usage

Add one or more instances of the "Timer Start" and "Timer Stop" nodes to an auth tree at the point where you'd like to start and stop your timer respectively.  Configure the name of the metric to update by setting the "Metric Key" property of the "Timer Stop" node".  If you plan to nest timers within a single tree, you can override the default property in which the start time is stored by setting the "Start Time Property" property in both nodes.

![ScreenShot](./example-tree.png)

Each time the "Timer Stop" node is processed, it will update the named metric:

![ScreenShot](./iproov-call-timer-metric.png)

## Enabling JMX

JMX access to AM metrics is disabled by default. To enable:

* login to the AM console as AM administrator, and navigate to Configure > Global Services > Monitoring.
* ensure "Monitoring Status" is enabled.
* ensure "Monitoring RMI interface status" is enabled.

You can use tools such as jconsole (included in the Java JDK /bin directory) to connect to AM via JMX.

## To Build

The code in this repository has binary dependencies that live in the ForgeRock maven repository. Maven can be configured to authenticate to this repository by following the following [ForgeRock Knowledge Base Article](https://backstage.forgerock.com/knowledge/kb/article/a74096897).

## Disclaimer
The sample code described herein is provided on an "as is" basis, without warranty of any kind, to the fullest extent permitted by law. ForgeRock does not warrant or guarantee the individual success developers may have in implementing the sample code on their development platforms or in production configurations.

ForgeRock does not warrant, guarantee or make any representations regarding the use, results of use, accuracy, timeliness or completeness of any data or information relating to the sample code. ForgeRock disclaims all warranties, expressed or implied, and in particular, disclaims all warranties of merchantability, and warranties related to the code, or any service or software related thereto.

ForgeRock shall not be liable for any direct, indirect or consequential damages or costs of any type arising out of any action taken by you or others related to the sample code.

[forgerock_platform]: https://www.forgerock.com/platform/  
