
import Foundation
import Alamofire
import SwiftyXMLParser
import UIKit

class getBusRouteListAPI {
    
    static let shared = getBusRouteListAPI()
      
    private init() {
    }
    
    func getData(sNum : String, completionHandler: @escaping (Result<[getBusRouteListData], Error>) -> Void) {
        
        //노선번호에 해당하는 노선 목록 조회
        let defaulturl = String(format: "%@", "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?")
        let ServiceKey = String(format: "ServiceKey=%@", BusAPIKey.API_Key)
        let strSrch = String(format: "&strSrch=%@", sNum)
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
                
                var getBusRouteListDataList : [getBusRouteListData] = []
                
                for item in xml["ServiceResult"]["msgBody"]["itemList"] {
                    
                    let busRouteId : String = item["busRouteId"].text!
                    let busRouteNm : String = item["busRouteNm"].text!
                    let edStationNm : String = item["edStationNm"].text!
                    let stStationNm : String = item["stStationNm"].text!
                    let term : String = item["term"].text!
                    
                    //print("busRouteId:\(busRouteId) busRouteNm:\(busRouteNm) edStationNm:\(edStationNm) stStationNm:\(stStationNm) term:\(term)")
                    getBusRouteListDataList.append(getBusRouteListData.init(busRouteId: busRouteId, busRouteNm: busRouteNm, edStationNm: edStationNm, stStationNm: stStationNm, term: term))
                }
                
                completionHandler(.success(getBusRouteListDataList))
                break
                
            case .failure(let error):
                completionHandler(.failure(error))
                break
            }
            
        }
        
    }
    
}
