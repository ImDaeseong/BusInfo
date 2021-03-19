
import Foundation

struct getBusRouteListData  {
    
    var busRouteId : String?
    var busRouteNm : String?
    var edStationNm : String?
    var stStationNm : String?
    var term : String?
    
    init( busRouteId : String, busRouteNm : String, edStationNm : String, stStationNm : String, term : String ) {
        
        self.busRouteId = busRouteId
        self.busRouteNm = busRouteNm
        self.edStationNm = edStationNm
        self.stStationNm = stStationNm
        self.term = term
    }
}
