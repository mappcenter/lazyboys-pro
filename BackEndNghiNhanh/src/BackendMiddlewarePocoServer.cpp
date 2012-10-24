#include "BackendMiddlewarePocoServer.h"

BackendMiddlewarePocoServer::BackendMiddlewarePocoServer() {
    serverMng = ServerManagerPtr(new ServerManager());
    logger = &Logger::get("BackendMiddlewarePocoServer");

}

BackendMiddlewarePocoServer::BackendMiddlewarePocoServer(const BackendMiddlewarePocoServer& orig) {

}

BackendMiddlewarePocoServer::~BackendMiddlewarePocoServer() {

}

int BackendMiddlewarePocoServer::main(const std::vector<std::string>& args) {

    //TestCase::compareString();
    int port = this->config().getInt("port");
    int numberThread = this->config().getInt("numberThread");
    string pathItemsDB = this->config().getString("pathHashDBOfItemsDB");
    string pathTagsDB = this->config().getString("pathHashDBOfTagsDB");
    string pathItemTagDB = this->config().getString("pathHashDBOfItemTagDB");
    string pathUserDB = this->config().getString("pathHashDBOfUserDB");
    string pathFeedBackDB = this->config().getString("pathHashDBOfFeedBackDB");
    std::string host = this->config().getString("host");
    std::cout << "Port = " << port << "\n";
    std::cout << "Host = " << host << "\n";
    TNonblockingServer server = serverMng->createConnection(port, numberThread, pathItemsDB, pathTagsDB,
            pathItemTagDB, pathUserDB, pathFeedBackDB);
    serverMng->listenning(server);
    waitForTerminationRequest();


    return 0;
}

void BackendMiddlewarePocoServer::initialize(Application& self) {
    loadConfiguration();
    BaseClass::initialize(self);
}

void BackendMiddlewarePocoServer::reinitialize(Application& self) {
    BaseClass::reinitialize(self);
}

void BackendMiddlewarePocoServer::uninitialize() {
    BaseClass::uninitialize();
}

void BackendMiddlewarePocoServer::defineOptions(OptionSet& options) {
    BaseClass::defineOptions(options);
    options.addOption(Option("help", "h", "display help information")
            .required(false)
            .repeatable(false));
    Option().callback(OptionCallback<BackendMiddlewarePocoServer > (this, &BackendMiddlewarePocoServer::handleOption));
}

void BackendMiddlewarePocoServer::handleOption(const std::string& name, const std::string& value) {
    if (name == "help") {
        displayHelp();
        stopOptionsProcessing();
    } else {
        BaseClass::handleOption(name, value);
    }
}

void BackendMiddlewarePocoServer::displayHelp() {
    HelpFormatter helpFormatter(options());
    helpFormatter.setCommand(commandName());
    helpFormatter.setUsage("[options]");
    helpFormatter.setHeader("options:");
    helpFormatter.format(std::cout);
}


POCO_SERVER_MAIN(BackendMiddlewarePocoServer);