/**
 * Copyright (C) 2014 Dell, Inc.
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

package org.dasein.cloud.platform.bigdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the support classes of the data warehouse support in Dasein Cloud
 * <p>Created by George Reese: 2/7/14 1:27 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 (issue #100)
 */
public class DataWarehouseTestCase {
    static private final String           CLUSTER_ID    = "clusterId";
    static private final DataClusterState CLUSTER_STATE = DataClusterState.AVAILABLE;
    static private final String           DB_NAME       = "dbName";
    static private final String           DESCRIPTION   = "description";
    static private final String           NAME          = "name";
    static private final int              PORT          = 17;
    static private final String           OWNER_ID      = "me";
    static private final String           PARAM_FAMILY  = "flintstones";
    static private final String           PRODUCT_ID    = "productId";
    static private final String           REGION_ID     = "regionId";

    private String adminPassword;
    private String adminUser;
    private boolean createEncrypted;
    private int    createPort;
    private String createVersion;
    private long   creationTimestamp;
    private String dataCenterId;
    private boolean encrypted;
    private String[] firewalls;
    private int    nodeCount;
    private Map<String,Object> parameters;
    private String parameterGroup;
    private ClusterQueryProtocol[] protocols;
    private String version;
    private String vlanId;

    @Before
    public void setUp() {
        dataCenterId = null;
        vlanId = null;
        adminUser = null;
        adminPassword = null;
        nodeCount = 1;
        version = "0";
        encrypted = false;
        createEncrypted = true;
        creationTimestamp = 0L;
        createPort = 0;
        createVersion = null;
        protocols = new ClusterQueryProtocol[0];
        parameterGroup = null;
        parameters = new HashMap<String,Object>();
        firewalls = new String[0];
    }

    @After
    public void tearDown() {

    }

    private void checkDataClusterContent(DataCluster cluster) {
        assertNotNull("The cluster returned from the constructor was illegally a null value", cluster);
        assertEquals("The cluster owner ID does not match the test value", OWNER_ID, cluster.getProviderOwnerId());
        assertEquals("The cluster region ID does not match the test value", REGION_ID, cluster.getProviderRegionId());
        assertEquals("The cluster data center ID does not match the test value", dataCenterId, cluster.getProviderDataCenterId());
        assertEquals("The cluster ID does not match the test value", CLUSTER_ID, cluster.getProviderDataClusterId());
        assertEquals("The name does not match the test value", NAME, cluster.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, cluster.getDescription());
        assertEquals("The product ID does not match the test value", PRODUCT_ID, cluster.getProviderProductId());
        assertEquals("The database name does not match the test value", DB_NAME, cluster.getDatabaseName());
        assertEquals("The port does not match the test value", PORT, cluster.getDatabasePort());
        assertEquals("The VLAN ID does not match the test value", vlanId, cluster.getProviderVlanId());
        assertEquals("The admin user does not match the test value", adminUser, cluster.getAdminUserName());
        assertEquals("The admin password does not match the test value", adminPassword, cluster.getAdminPassword());
        assertEquals("The node count does not match the test value", nodeCount, cluster.getNodeCount());
        assertEquals("The version does not match the test value", version, cluster.getClusterVersion());
        assertEquals("The parameter group does not match the test value", parameterGroup, cluster.getProviderParameterGroupId());

        ClusterQueryProtocol[] p = cluster.getProtocols();

        assertNotNull("The protocols supported by a data cluster cannot be null", p);
        assertEquals("The number of protocols must match", protocols.length, p.length);
        assertArrayEquals("The protocols must match", protocols, p);
    }

    private void checkDataClusterCreateOptionsContent(DataClusterCreateOptions options) {
        assertNotNull("The data cluster create options may not be null", options);
        assertEquals("The data center ID does not match the test value", dataCenterId, options.getProviderDataCenterId());
        assertEquals("The product ID does not match the test value", PRODUCT_ID, options.getProviderProductId());
        assertEquals("The name does not match the test value", NAME, options.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, options.getDescription());
        assertEquals("The node count does not match the test value", nodeCount, options.getNodeCount());
        if( adminUser == null ) {
            assertNotNull("The default admin user may not be null", options.getAdminUserName());
        }
        else {
            assertEquals("The admin user does not match the test value", adminUser, options.getAdminUserName());
        }
        if( adminPassword == null ) {
            assertNotNull("The default admin password may not be null", options.getAdminPassword());
        }
        else {
            assertEquals("The admin password does not match the test value", adminPassword, options.getAdminPassword());
        }
        assertEquals("The cluster version does not match the test value", createVersion, options.getClusterVersion());
        assertEquals("The database name does not match the test value", DB_NAME, options.getDatabaseName());
        assertEquals("The database port does not match the test or expected default value", createPort, options.getDatabasePort());
        assertEquals("The encrypted value does not match the test value", createEncrypted,  options.isEncrypted());
        assertEquals("The parameter group does not match the test value", parameterGroup, options.getProviderParameterGroupId());
        assertArrayEquals("The firewall IDs do not match the test value", firewalls, options.getProviderFirewallIds());
    }

    private void checkDataClusterProductContent(DataClusterProduct product) {
        assertNotNull("The data cluster product may not be null", product);
        assertEquals("The product ID does not match the test value", PRODUCT_ID, product.getProviderProductId());
        assertEquals("The name does not match the test value", NAME, product.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, product.getDescription());
    }

    private void checkDataClusterVersionContent(DataClusterVersion version) {
        assertNotNull("The data cluster version may not be null", version);
        assertEquals("The version number does not match the test value", createVersion, version.getVersionNumber());
        assertEquals("The parameter family does not match the test value", PARAM_FAMILY, version.getParameterFamily());
        assertEquals("The name does not match the test value", NAME, version.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, version.getDescription());
    }

    private void checkDataClusterParameterGroupContent(DataClusterParameterGroup group) {
        assertNotNull("The parameter group may not be null", group);
        assertEquals("The parameter group ID does not match", parameterGroup, group.getProviderGroupId());
        assertEquals("The family does not match the test value", PARAM_FAMILY, group.getFamily());
        assertEquals("The name does not match the test value", NAME, group.getName());
        assertEquals("The description does not match the test value", DESCRIPTION, group.getDescription());

        Set<String> keys = group.getParameters().keySet();
        String[] actualKeys = keys.toArray(new String[keys.size()]);

        keys = parameters.keySet();
        String[] expectedKeys = keys.toArray(new String[keys.size()]);

        assertArrayEquals("The available parameters do not match", expectedKeys, actualKeys);

        for( String key : expectedKeys ) {
            assertEquals("The parameter value for " + key + " does not match the test value", parameters.get(key), group.getParameters().get(key));
        }
    }

    @Test
    public void verifyDataClusterSimpleConstructor() {
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterSimpleConstructorWithProtocols() {
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC };
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, protocols));
    }

    @Test
    public void verifyDataClusterSimpleConstructorWithValidDC() {
        dataCenterId = "dataCenterId";
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterSimpleVLANConstructor() {
        dataCenterId = "dataCenterId";
        vlanId = "vlanId";
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT));
    }

    @Test
    public void verifyDataClusterUserConstructor() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        nodeCount = 2;
        checkDataClusterContent(DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, adminUser, adminPassword, nodeCount));
    }

    @Test
    public void verifyDataClusterFullConstructor() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp));
    }

    @Test
    public void verifyDataClusterFullConstructorWithOneProtocol() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC };
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp, protocols));
    }

    @Test
    public void verifyDataClusterFullConstructorWithTwoProtocols() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC, ClusterQueryProtocol.ODBC };
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp, protocols));
    }

    @Test
    public void verifyDataClusterFullConstructorWithCreationTimestamp() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;
        creationTimestamp = System.currentTimeMillis();
        checkDataClusterContent(DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp, protocols));
    }

    @Test
    public void verifyDataClusterAlterCredentials() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        adminUser = "admin";
        adminPassword = "password";
        c.havingAdminCredentials(adminUser, adminPassword);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterNodeCount() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        nodeCount = 3;
        c.havingNodeCount(nodeCount);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterParameterGroup() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        parameterGroup = "soda";
        c.withParameterGroup(parameterGroup);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterEncryptionOn() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        encrypted = true;
        c.withEncryption();
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterEncryptionOff() {
        dataCenterId = "dataCenterId";
        adminUser = "admin";
        adminPassword = "password";
        vlanId = "vlanId";
        nodeCount = 2;
        version = "1.0";
        encrypted = true;

        DataCluster c = DataCluster.getInstance(vlanId, OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, version, DB_NAME, PORT, adminUser, adminPassword, nodeCount, encrypted, creationTimestamp);

        encrypted = false;
        c.withoutEncryption();
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterVersion() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        version = "2.0";
        c.usingVersion(version);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterVLAN() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        vlanId = "vlanId";
        c.inVlan(vlanId);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterProtocolsWithNone() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.ODBC };
        c.supportingProtocols(protocols);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterProtocolsWithOne() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT, ClusterQueryProtocol.JDBC);

        c.supportingProtocols(ClusterQueryProtocol.ODBC);
        protocols = new ClusterQueryProtocol[] { ClusterQueryProtocol.JDBC, ClusterQueryProtocol.ODBC };
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterAlterCreationTimestamp() {
        DataCluster c = DataCluster.getInstance(OWNER_ID, REGION_ID, dataCenterId, CLUSTER_ID, CLUSTER_STATE, NAME, DESCRIPTION, PRODUCT_ID, DB_NAME, PORT);

        creationTimestamp = System.currentTimeMillis();
        c.createdAt(creationTimestamp);
        checkDataClusterContent(c);
    }

    @Test
    public void verifyDataClusterCreateOptionsSimpleConstructor() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyDataClusterCreateOptionsSimpleConstructorWithDC() {
        dataCenterId = "moon";

        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, dataCenterId, NAME, DESCRIPTION, DB_NAME);

        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyDataClusterCreateOptionsFullConstructor() {
        dataCenterId = "moon";
        createEncrypted = false;
        nodeCount = 7;
        createVersion = "9.34";
        createPort = 1701;
        adminUser = "spiderman";
        adminPassword = "olympics";

        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, dataCenterId, NAME, DESCRIPTION, createVersion, DB_NAME, createPort, adminUser, adminPassword, nodeCount, createEncrypted);

        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterEncryption() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        createEncrypted = false;
        options.withoutEncryption();
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterFirewalls() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        firewalls = new String[] { "a", "b" };
        options.behindFirewalls(firewalls);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterParameterGroup() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        parameterGroup = "vikings";
        options.withParameterGroup(parameterGroup);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterCredentials() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        adminUser = "wonderwoman";
        adminPassword = "airplane";
        options.havingAdminCredentials(adminUser, adminPassword);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterNodeCount() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        nodeCount = 12;
        options.havingNodeCount(nodeCount);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyCreateAlterDC() {
        DataClusterCreateOptions options = DataClusterCreateOptions.getInstance(PRODUCT_ID, NAME, DESCRIPTION, DB_NAME);

        dataCenterId = "mars";
        options.inDataCenter(dataCenterId);
        checkDataClusterCreateOptionsContent(options);
    }

    @Test
    public void verifyProductConstructor() {
        DataClusterProduct product = DataClusterProduct.getInstance(PRODUCT_ID, NAME, DESCRIPTION);

        checkDataClusterProductContent(product);
    }

    @Test
    public void verifyVersionConstructor() {
        createVersion = "91.5fm";
        DataClusterVersion version = DataClusterVersion.getInstance(createVersion, PARAM_FAMILY, NAME, DESCRIPTION);

        checkDataClusterVersionContent(version);
    }

    @Test
    public void verifyParameterGroupConstructorNoParams() {
        parameterGroup = "alpha";
        DataClusterParameterGroup group = DataClusterParameterGroup.getInstance(parameterGroup, PARAM_FAMILY, NAME, DESCRIPTION, parameters);

        checkDataClusterParameterGroupContent(group);
    }

    @Test
    public void verifyParameterGroupConstructorWithParams() {
        parameterGroup = "alpha";
        parameters.put("hello", "goodbye");
        DataClusterParameterGroup group = DataClusterParameterGroup.getInstance(parameterGroup, PARAM_FAMILY, NAME, DESCRIPTION, parameters);

        checkDataClusterParameterGroupContent(group);
    }
}
