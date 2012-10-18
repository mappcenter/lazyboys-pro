/* 
 * File:   TestCase.h
 * Author: toantm
 *
 * Created on October 3, 2012, 8:35 AM
 */

#ifndef TESTCASE_H
#define	TESTCASE_H
#include "ItemDB.h"
#include "Utils.h"
#include "Poco/RegularExpression.h"
#include "Poco/TextEncoding.h"
#include "Poco/String.h"
#include <vector>
#include <iostream>
#include <string>
using namespace std;
using Poco::RegularExpression;
using Poco::replace;

using namespace Poco;

class TestCase {
public:
    TestCase();
    TestCase(const TestCase& orig);
    virtual ~TestCase();
public:
    static void testInsertItem(ItemDB& itemDB);
    static void testDeleteItem(ItemDB& itemDB);
    static void testEditItem(ItemDB& itemDB);
    static bool compareString();
private:

};

#endif	/* TESTCASE_H */

