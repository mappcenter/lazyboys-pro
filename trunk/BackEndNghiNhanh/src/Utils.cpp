/* 
 * File:   Utils.cpp
 * Author: toantm
 * 
 * Created on September 26, 2012, 5:02 PM
 */

#include "Utils.h"

Utils::Utils() {
}

Utils::Utils(const Utils& orig) {
}

Utils::~Utils() {
}

/**
 * convert from int64_t to string
 * @param number
 * @return string
 */
string Utils::convertIntToString(int64_t number) {
    string result = NumberFormatter::format(number);
    return result;
}

/**
 * Convert from string to int64_t
 * @param str
 * @return int64_t
 */
int64_t Utils::convertStringToInt(string str) {
    int64_t result = NumberParser::parse64(str);
    return result;
}

/**
 * Get time now.
 * @return string
 */
string Utils::getTimeNow() {
    time_t rawtime;
    time(&rawtime);
    return ctime(&rawtime);
}

/**
 * Random 1 so int64_t
 * @param max
 * @return  int64_t
 */
int64_t Utils::getRandomNumber(int64_t max) {
    srand((unsigned) Timestamp().utcTime());
    return (rand() % max);
}

/**
 * Tim chuoi str2 co nam trong chuoi str1 hay khong.
 * @param str1
 * @param str2
 * @return bool true neu co, false neu khong co
 */
bool Utils::findStringInString(string str1, string str2) {
    //str1 = toLowerInPlace(str1);
    //str2 = toLowerInPlace(str2);
    size_t found = str1.find(str2);
    if(found == string::npos){
        return false;
    }else{
        return true;
    }
}
bool Utils::findStringInStringForTag(string str1, string str2) {
    toLowerInPlace(str1);
    toLowerInPlace(str2);
    size_t found = str1.find(str2);
    if(found == string::npos){
        return false;
    }else{
        return true;
    }
}