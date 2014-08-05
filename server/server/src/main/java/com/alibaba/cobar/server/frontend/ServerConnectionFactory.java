/*
 * Copyright 1999-2012 Alibaba Group.
 *  
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
 */
package com.alibaba.cobar.server.frontend;

import java.nio.channels.SocketChannel;

import com.alibaba.cobar.config.model.SystemConfig;
import com.alibaba.cobar.server.CobarPrivileges;
import com.alibaba.cobar.server.CobarServer;
import com.alibaba.cobar.server.frontend.session.BlockingSession;
import com.alibaba.cobar.server.frontend.session.NonBlockingSession;
import com.alibaba.cobar.server.net.FrontendConnection;
import com.alibaba.cobar.server.net.factory.FrontendConnectionFactory;

/**
 * @author xianmao.hexm
 */
public class ServerConnectionFactory extends FrontendConnectionFactory {

    @Override
    protected FrontendConnection getConnection(SocketChannel channel) {
        SystemConfig sys = CobarServer.getInstance().getConfig().getSystem();
        ServerConnection c = new ServerConnection(channel);
        c.setPrivileges(new CobarPrivileges());
        c.setQueryHandler(new ServerQueryHandler(c));
        // c.setPrepareHandler(new ServerPrepareHandler(c)); TODO prepare
        c.setTxIsolation(sys.getTxIsolation());
        c.setSession(new BlockingSession(c));
        c.setSession2(new NonBlockingSession(c));
        return c;
    }

}
