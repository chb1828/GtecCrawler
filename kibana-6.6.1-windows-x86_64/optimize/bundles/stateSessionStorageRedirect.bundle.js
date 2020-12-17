/*! Copyright Elasticsearch B.V. and/or license to Elasticsearch B.V. under one or more contributor license agreements
 * Licensed under the Elastic License; you may not use this file except in compliance with the Elastic License. */(function(modules){function webpackJsonpCallback(data){var chunkIds=data[0];var moreModules=data[1];var executeModules=data[2];var moduleId,chunkId,i=0,resolves=[];for(;i<chunkIds.length;i++){chunkId=chunkIds[i];if(installedChunks[chunkId]){resolves.push(installedChunks[chunkId][0])}installedChunks[chunkId]=0}for(moduleId in moreModules){if(Object.prototype.hasOwnProperty.call(moreModules,moduleId)){modules[moduleId]=moreModules[moduleId]}}if(parentJsonpFunction)parentJsonpFunction(data);while(resolves.length){resolves.shift()()}deferredModules.push.apply(deferredModules,executeModules||[]);return checkDeferredModules()}function checkDeferredModules(){var result;for(var i=0;i<deferredModules.length;i++){var deferredModule=deferredModules[i];var fulfilled=true;for(var j=1;j<deferredModule.length;j++){var depId=deferredModule[j];if(installedChunks[depId]!==0)fulfilled=false}if(fulfilled){deferredModules.splice(i--,1);result=__webpack_require__(__webpack_require__.s=deferredModule[0])}}return result}var installedModules={};var installedChunks={13:0};var deferredModules=[];function __webpack_require__(moduleId){if(installedModules[moduleId]){return installedModules[moduleId].exports}var module=installedModules[moduleId]={i:moduleId,l:false,exports:{}};modules[moduleId].call(module.exports,module,module.exports,__webpack_require__);module.l=true;return module.exports}__webpack_require__.m=modules;__webpack_require__.c=installedModules;__webpack_require__.d=function(exports,name,getter){if(!__webpack_require__.o(exports,name)){Object.defineProperty(exports,name,{enumerable:true,get:getter})}};__webpack_require__.r=function(exports){if(typeof Symbol!=="undefined"&&Symbol.toStringTag){Object.defineProperty(exports,Symbol.toStringTag,{value:"Module"})}Object.defineProperty(exports,"__esModule",{value:true})};__webpack_require__.t=function(value,mode){if(mode&1)value=__webpack_require__(value);if(mode&8)return value;if(mode&4&&typeof value==="object"&&value&&value.__esModule)return value;var ns=Object.create(null);__webpack_require__.r(ns);Object.defineProperty(ns,"default",{enumerable:true,value:value});if(mode&2&&typeof value!="string")for(var key in value)__webpack_require__.d(ns,key,function(key){return value[key]}.bind(null,key));return ns};__webpack_require__.n=function(module){var getter=module&&module.__esModule?function getDefault(){return module["default"]}:function getModuleExports(){return module};__webpack_require__.d(getter,"a",getter);return getter};__webpack_require__.o=function(object,property){return Object.prototype.hasOwnProperty.call(object,property)};__webpack_require__.p="__REPLACE_WITH_PUBLIC_PATH__";var jsonpArray=window["webpackJsonp"]=window["webpackJsonp"]||[];var oldJsonpFunction=jsonpArray.push.bind(jsonpArray);jsonpArray.push=webpackJsonpCallback;jsonpArray=jsonpArray.slice();for(var i=0;i<jsonpArray.length;i++)webpackJsonpCallback(jsonpArray[i]);var parentJsonpFunction=oldJsonpFunction;deferredModules.push([4985,0]);return checkDeferredModules()})({4985:function(module,exports,__webpack_require__){"use strict";__webpack_require__(75);__webpack_require__(76);__webpack_require__(77);__webpack_require__(78);__webpack_require__(79);__webpack_require__(80);__webpack_require__(81);__webpack_require__(82);__webpack_require__(83);__webpack_require__(84);__webpack_require__(85);__webpack_require__(86);__webpack_require__(87);__webpack_require__(88);__webpack_require__(89);__webpack_require__(90);__webpack_require__(91);__webpack_require__(92);__webpack_require__(93);__webpack_require__(94);__webpack_require__(95);__webpack_require__(96);__webpack_require__(97);__webpack_require__(98);__webpack_require__(99);__webpack_require__(100);__webpack_require__(101);__webpack_require__(102);__webpack_require__(103);__webpack_require__(104);__webpack_require__(105);__webpack_require__(106);__webpack_require__(107);__webpack_require__(108);__webpack_require__(109);__webpack_require__(110);__webpack_require__(111);__webpack_require__(112);__webpack_require__(113);__webpack_require__(114);__webpack_require__(115);__webpack_require__(116);__webpack_require__(117);__webpack_require__(118);__webpack_require__(119);__webpack_require__(120);__webpack_require__(121);__webpack_require__(122);__webpack_require__(123);__webpack_require__(124);__webpack_require__(125);__webpack_require__(126);__webpack_require__(127);__webpack_require__(128);__webpack_require__(129);__webpack_require__(130);__webpack_require__(131);__webpack_require__(132);__webpack_require__(133);__webpack_require__(134);__webpack_require__(135);__webpack_require__(136);__webpack_require__(137);__webpack_require__(138);__webpack_require__(139);__webpack_require__(140);__webpack_require__(141);__webpack_require__(142);__webpack_require__(143);__webpack_require__(144);__webpack_require__(145);__webpack_require__(146);__webpack_require__(147);__webpack_require__(148);__webpack_require__(149);__webpack_require__(150);__webpack_require__(151);__webpack_require__(152);__webpack_require__(153);__webpack_require__(154);__webpack_require__(155);__webpack_require__(156);__webpack_require__(157);__webpack_require__(158);__webpack_require__(159);__webpack_require__(160);__webpack_require__(161);__webpack_require__(162);__webpack_require__(163);__webpack_require__(164);__webpack_require__(165);__webpack_require__(166);__webpack_require__(167);__webpack_require__(168);__webpack_require__(169);__webpack_require__(170);__webpack_require__(171);__webpack_require__(172);__webpack_require__(173);__webpack_require__(185);var _i18n=__webpack_require__(9);var _kibanaCore__=__webpack_require__(174);var injectedMetadata=JSON.parse(document.querySelector("kbn-injected-metadata").getAttribute("data"));_i18n.i18n.init(injectedMetadata.legacyMetadata.translations);new _kibanaCore__.CoreSystem({injectedMetadata:injectedMetadata,rootDomElement:document.body,requireLegacyFiles:function requireLegacyFiles(){__webpack_require__(4986)}}).start()},4986:function(module,exports,__webpack_require__){"use strict";__webpack_require__(255);var _chrome=__webpack_require__(8);var _chrome2=_interopRequireDefault(_chrome);var _state_hashing=__webpack_require__(291);var _routes=__webpack_require__(12);var _routes2=_interopRequireDefault(_routes);function _interopRequireDefault(obj){return obj&&obj.__esModule?obj:{default:obj}}_routes2.default.enable();_routes2.default.when("/",{resolve:{url:function url(AppState,globalState,$window){var redirectUrl=_chrome2.default.getInjected("redirectUrl");var hashedUrl=(0,_state_hashing.hashUrl)([new AppState,globalState],redirectUrl);var url=_chrome2.default.addBasePath(hashedUrl);$window.location=url}}})},7:function(module,exports){module.exports=vendors}});