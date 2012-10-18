
#include "ServerManager.h"

ServerManager::ServerManager() {
    logger = &Logger::get("ServerManager");
    //logger = &Logger::set
}

ServerManager::ServerManager(const ServerManager& orig) {
}

ServerManager::~ServerManager() {
}

TNonblockingServer ServerManager::createConnection(int port, string pathItemsDB, string pathTagsDB,
        string pathItemTagDB, string pathUserDB, string pathFeedBackDB) {
    boost::shared_ptr<BackendMiddlewareHandler> handler(new BackendMiddlewareHandler(pathItemsDB, pathTagsDB,
            pathItemTagDB, pathUserDB, pathFeedBackDB));
    boost::shared_ptr<TProcessor> processor(new BackendMiddlewareProcessor(handler));
    boost::shared_ptr<TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());

    // using thread pool with maximum 15 threads to handle incoming requests
    boost::shared_ptr<ThreadManager> threadManager = ThreadManager::newSimpleThreadManager(15);
    boost::shared_ptr<PosixThreadFactory> threadFactory = boost::shared_ptr<PosixThreadFactory > (new PosixThreadFactory());
    threadManager->threadFactory(threadFactory);
    threadManager->start();

    poco_information(*logger, "Created TNonbocking server.");
    printf("Created TNonbocking server.\n");
    TNonblockingServer server(processor, protocolFactory, port, threadManager);
    return server;
}

void ServerManager::listenning(TNonblockingServer server) {
    poco_information(*logger, "Server is listening...");
    printf("Server is listening...\n");
    server.serve();
}