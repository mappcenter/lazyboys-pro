#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/src/BackendMiddlewareHandler.o \
	${OBJECTDIR}/src/FeedBackDB.o \
	${OBJECTDIR}/src/BackendMiddlewarePocoServer.o \
	${OBJECTDIR}/gen-cpp/BackendMiddleware.o \
	${OBJECTDIR}/src/TagDB.o \
	${OBJECTDIR}/src/ItemTagDB.o \
	${OBJECTDIR}/src/TestCase.o \
	${OBJECTDIR}/src/ServerManager.o \
	${OBJECTDIR}/src/UserDB.o \
	${OBJECTDIR}/src/Utils.o \
	${OBJECTDIR}/gen-cpp/BackendMiddleware_types.o \
	${OBJECTDIR}/src/ItemDB.o \
	${OBJECTDIR}/src/DBUtils.o \
	${OBJECTDIR}/gen-cpp/BackendMiddleware_constants.o \
	${OBJECTDIR}/src/synchronizeDB.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=-L/zserver/lib -Wl,-rpath,/zserver/lib -lthriftnb -levent -lthrift -lPocoFoundationd -lPocoUtild -lkyotocabinet -ljson_linux-gcc-4.6_libmt -lboost_thread

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk bin/backendnghinhanhd

bin/backendnghinhanhd: ${OBJECTFILES}
	${MKDIR} -p bin
	${LINK.cc} -o bin/backendnghinhanhd ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/src/BackendMiddlewareHandler.o: src/BackendMiddlewareHandler.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/BackendMiddlewareHandler.o src/BackendMiddlewareHandler.cpp

${OBJECTDIR}/src/FeedBackDB.o: src/FeedBackDB.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/FeedBackDB.o src/FeedBackDB.cpp

${OBJECTDIR}/src/BackendMiddlewarePocoServer.o: src/BackendMiddlewarePocoServer.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/BackendMiddlewarePocoServer.o src/BackendMiddlewarePocoServer.cpp

${OBJECTDIR}/gen-cpp/BackendMiddleware.o: gen-cpp/BackendMiddleware.cpp 
	${MKDIR} -p ${OBJECTDIR}/gen-cpp
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/gen-cpp/BackendMiddleware.o gen-cpp/BackendMiddleware.cpp

${OBJECTDIR}/src/TagDB.o: src/TagDB.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/TagDB.o src/TagDB.cpp

${OBJECTDIR}/src/ItemTagDB.o: src/ItemTagDB.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/ItemTagDB.o src/ItemTagDB.cpp

${OBJECTDIR}/src/TestCase.o: src/TestCase.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/TestCase.o src/TestCase.cpp

${OBJECTDIR}/src/ServerManager.o: src/ServerManager.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/ServerManager.o src/ServerManager.cpp

${OBJECTDIR}/src/UserDB.o: src/UserDB.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/UserDB.o src/UserDB.cpp

${OBJECTDIR}/src/Utils.o: src/Utils.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/Utils.o src/Utils.cpp

${OBJECTDIR}/gen-cpp/BackendMiddleware_types.o: gen-cpp/BackendMiddleware_types.cpp 
	${MKDIR} -p ${OBJECTDIR}/gen-cpp
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/gen-cpp/BackendMiddleware_types.o gen-cpp/BackendMiddleware_types.cpp

${OBJECTDIR}/src/ItemDB.o: src/ItemDB.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/ItemDB.o src/ItemDB.cpp

${OBJECTDIR}/src/DBUtils.o: src/DBUtils.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/DBUtils.o src/DBUtils.cpp

${OBJECTDIR}/gen-cpp/BackendMiddleware_constants.o: gen-cpp/BackendMiddleware_constants.cpp 
	${MKDIR} -p ${OBJECTDIR}/gen-cpp
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/gen-cpp/BackendMiddleware_constants.o gen-cpp/BackendMiddleware_constants.cpp

${OBJECTDIR}/src/synchronizeDB.o: src/synchronizeDB.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.cc) -g -DHAVE_CONFIG_H -DHAVE_NETINET_IN_H -I/zserver/include -I/data/zserver/include/thrift -Igen-cpp -Iinc -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/synchronizeDB.o src/synchronizeDB.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} bin/backendnghinhanhd

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
