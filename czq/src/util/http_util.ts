import axios from "axios";

const domain = '/api/';

function get(uri: string, data: object, callbackFunc: (rep: any) => void) {
    axios.get(splicingURL(uri), data).then(callbackFunc, errFunc)
}

function post(uri:string, data:object, callbackFunc: (res: any) => void) {
    axios.post(splicingURL(uri), data).then(callbackFunc, errFunc)
}

function del(uri:string, data:object, callbackFunc: (res: any) => void) {
    axios.delete(splicingURL(uri), data).then(callbackFunc, errFunc)
}

function put(uri:string, data:object, callbackFunc: (res: any) => void) {
    axios.put(splicingURL(uri), data).then(callbackFunc, errFunc)
}



function splicingURL(uri:string) {
    if (uri.indexOf('/') == (uri.length - 1)) {
        uri.substring(0, length - 2)
    }
    return domain + uri
}

function errFunc(e: any) {
    console.error(e)
}

export {
    get,
    put,
    post,
    del
}