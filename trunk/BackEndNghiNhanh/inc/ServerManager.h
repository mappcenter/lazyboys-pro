/* 
 * File:   ServerManager.h
 * Author: toantm
 *
 * Created on September 20, 2012, 2:50 PM
 */

#ifndef SERVERMANAGER_H
#define	SERVERMANAGER_H

#include <server/TNonblockingServer.h>
#include <Poco/Logger.h>
#include "BackendMiddlewareHandler.h"
#include <concurrency/ThreadManager.h>
#include <concurrency/PosixThreadFactory.h>
#include <server/TThreadPoolServer.h>
#include <server/TThreadedServer.h>

using namespace apache::thrift;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;
using namespace apache::thrift::server;
using Poco::Logger;
using namespace apache::thrift::concurrency;

class ServerManager {
public:
    ServerManager();
    ServerManager(const ServerManager& orig);
    virtual ~ServerManager();
    
public:
    TNonblockingServer createConnection(int port, string pathItemsDB, string pathTagsDB,
        string pathItemTagDB, string pathUserDB, string pathFeedBackDB);
    void listenning(TNonblockingServer server);
    
private:
    Logger* logger;

};

#endif	/* SERVERMANAGER_H */

