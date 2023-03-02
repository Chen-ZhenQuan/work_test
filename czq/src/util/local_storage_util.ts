const getPageData = () => {
    let pageSize = localStorage.getItem("pageSize")
    let pageNum = localStorage.getItem("pageNum")
    let searchValue = localStorage.getItem("searchValue")

    return {
        pageSize: pageSize == null ? '10' : pageSize,
        pageNum: pageNum == null ? '1' : pageNum,
        searchValue: searchValue == null ? "" : searchValue
    }
}

const setPageData = (size: string, num: string, searchValue: string) => {
    localStorage.setItem('pageSize', size + '')
    localStorage.setItem('pageNum', num + '')
    localStorage.setItem('searchValue', searchValue + '')
}


export {
    getPageData,
    setPageData
}