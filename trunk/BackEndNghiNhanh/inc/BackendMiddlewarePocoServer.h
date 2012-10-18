/* 
 * File:   BackendMiddlewarePocoServer.h
 * Author: toantm
 *
 * Created on September 20, 2012, 3:00 PM
 */

#ifndef BACKENDMIDDLEWAREPOCOSERVER_H
#define	BACKENDMIDDLEWAREPOCOSERVER_H

#include <iostream>
#include <string.h>
#include "Poco/Logger.h"
#include <Poco/Util/ServerApplication.h>
#include <Poco/Util/Application.h>
#include <Poco/Util/Option.h>
#include <Poco/Util/OptionCallback.h>
#include <Poco/Util/OptionSet.h>
#include <Poco/Util/HelpFormatter.h>
#include <boost/shared_ptr.hpp>
#include <kchashdb.h>
#include <kccachedb.h>
#include "ServerManager.h"
#include "DBUtils.h"
#include "TestCase.h"
#include "Poco/RegularExpression.h"

using Poco::Util::ServerApplication;
using Poco::Util::Application;
using Poco::Util::Option;
using Poco::Util::OptionCallback;
using Poco::Util::OptionSet;
using Poco::Util::HelpFormatter;
using Poco::Logger;
using Poco::RegularExpression;
using boost::shared_ptr;
using namespace std;
using namespace kyotocabinet;

class BackendMiddlewarePocoServer : public ServerApplication {
public:
    typedef ServerApplication BaseClass;
public:
    BackendMiddlewarePocoServer();
    BackendMiddlewarePocoServer(const BackendMiddlewarePocoServer& orig);
    virtual ~BackendMiddlewarePocoServer();
public:
    int main(const std::vector<std::string>& args);
    void initialize(Application& self);
    void reinitialize(Application& self);
    void uninitialize();
    void defineOptions(OptionSet& options);
    void handleOption(const std::string& name, const std::string& value);
public:
    string getPathDatabase();
private:
    void displayHelp();
    typedef boost::shared_ptr<ServerManager> ServerManagerPtr;
    ServerManagerPtr serverMng;
    //DatabaseUtils dbManager;
    //GrassDB grassDB;
    Logger* logger;
};

#endif	/* BACKENDMIDDLEWAREPOCOSERVER_H */

