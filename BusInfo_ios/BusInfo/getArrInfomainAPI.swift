
import Foundation
import Alamofire
import SwiftyXMLParser
import UIKit

class getArrInfomainAPI {
    
    static let shared = getArrInfomainAPI()
      
    private init() {
    }
    
    func getData(sbusRouteId : String, completionHandler: @escaping (Result<[getArrInfoByRouteAllData], Error>) -> Void) {
        
        //노선번호에 해당하는 노선 목록 조회
        let defaulturl = String(format: "%@", "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?")
        let ServiceKey = String(format: "ServiceKey=%@", BusAPIKey.API_Key)
        let strSrch = String(format: "&busRouteId=%@", sbusRouteId)
        let sUrl  = String(format: "%@%@%@", defaulturl, ServiceKey, strSrch)
        //print(sUrl)
        
        guard let baseURL = URL(string: sUrl) else {
            return
        }
        
        AF.request(baseURL).response { response in
            
            //let status = response.response?.statusCode
            
            switch response.result {
            case .success:
                
                let xml = XML.parse(response.data!)
                
                var getArrInfoByRouteAllDataList : [getArrInfoByRouteAllData] = []
                
                for item in xml["ServiceResult"]["msgBody"]["itemList"] {
                    
                    let arrmsg1 : String = item["arrmsg1"].text!
                    let arrmsg2 : String = item["arrmsg2"].text!
                    let arsId : String = item["arsId"].text!
                    let rtNm : String = item["rtNm"].text!
                    let stId : String = item["stId"].text!
                    let stNm : String = item["stNm"].text!
                    
                    //print("arrmsg1:\(arrmsg1) arrmsg2:\(arrmsg2) arsId:\(arsId) rtNm:\(rtNm) stId:\(stId) stNm:\(stNm)")
                    getArrInfoByRouteAllDataList.append(getArrInfoByRouteAllData.init(arrmsg1: arrmsg1, arrmsg2: arrmsg2, arsId: arsId, rtNm: rtNm, stId: stId, stNm: stNm))
                }
                
                completionHandler(.success(getArrInfoByRouteAllDataList))
                break
                
            case .failure(let error):
                completionHandler(.failure(error))
                break
            }
            
        }
        
    }
    
}
