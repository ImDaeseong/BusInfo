
import Foundation

struct getArrInfoByRouteAllData  {
    
    var arrmsg1 : String?
    var arrmsg2 : String?
    var arsId : String?
    var rtNm : String?
    var stId : String?
    var stNm : String?
    
    init( arrmsg1 : String, arrmsg2 : String, arsId : String, rtNm : String, stId : String, stNm : String ) {
        
        self.arrmsg1 = arrmsg1
        self.arrmsg2 = arrmsg2
        self.arsId = arsId
        self.rtNm = rtNm
        self.stId = stId
        self.stNm = stNm
    }
}
