/* 
 * File:   Utils.h
 * Author: toantm
 *
 * Created on September 26, 2012, 5:02 PM
 */

#ifndef UTILS_H
#define	UTILS_H
#include <stdio.h>
#include <string>
#include <sstream>
#include <cstdlib>
#include <utility>
#include <fstream>
#include <iostream>
#include <json/reader.h>
#include <json/value.h>
#include <json/writer.h>
#include <string.h>
#include <kchashdb.h>
#include <kccachedb.h>
#include <time.h>
#include "Poco/NumberFormatter.h"
#include "Poco/NumberParser.h"
#include "Poco/Timestamp.h"
#include "Poco/String.h"
#include "Poco/RegularExpression.h"
        


using namespace std;
using namespace Poco;
using Poco::NumberFormatter;
using Poco::NumberParser;
using Poco::toLower;
using Poco::RegularExpression;

const string ADD = "add";
const string UPDATE = "update";
const string DELETE = "delete";

class Utils {
public:

    Utils();
    Utils(const Utils& orig);
    virtual ~Utils();
public:
    static string convertIntToString(int64_t number);
    static int64_t convertStringToInt(string str);
    static string getTimeNow();
    static int64_t getRandomNumber(int64_t max);
    static bool findStringInString(string str1, string str2);
    static bool findStringInStringRe(string str1, string str2);
    static bool findStringInStringForTag(string str1, string str2);
private:

};

#endif	/* UTILS_H */

