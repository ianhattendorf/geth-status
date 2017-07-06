package com.ianhattendorf.geth.gethstatus;

import com.ianhattendorf.geth.gethstatus.service.FreeGeoServiceTest;
import com.ianhattendorf.geth.gethstatus.service.IpifyPublicIpServiceTest;
import com.ianhattendorf.geth.gethstatus.service.RpcGethServiceTest;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.rules.ExternalResource;

import java.io.IOException;

public class ExternalServersRule extends ExternalResource {

    private MockWebServer gethServer;
    private MockWebServer ipifyPublicIpServer;
    private MockWebServer freeGeoServer;

    @Override
    protected void before() throws Throwable {
        super.before();
        gethServer = TestHelper.initMockServer(8088, new RpcGethServiceTest.RpcDispatcher(false));
        ipifyPublicIpServer = TestHelper.initMockServer(8089, new IpifyPublicIpServiceTest.IpifyPublicIpDispatcher());
        freeGeoServer = TestHelper.initMockServer(8090, new FreeGeoServiceTest.FreeGeoDispatcher());
    }

    @Override
    protected void after() {
        super.after();
        try {
            gethServer.shutdown();
            ipifyPublicIpServer.shutdown();
            freeGeoServer.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
