
import UIKit

class LabelEx : UILabel {
     
    @IBInspectable var grayColor : Bool = false {
        didSet {
            
            if (grayColor == true) {
                
                self.textAlignment = .right
                self.textColor = UIColor.labelgrayColor()
            } else {
                
                self.textAlignment = .left
                self.textColor = UIColor.black
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        initLabel()
    }
    
    required override init(frame: CGRect) {
        super.init(frame: frame)
        initLabel()
    }
    
    private func initLabel(){
        
        self.textAlignment = .left
        self.font = UIFont.systemFont(ofSize: 12)
        self.textColor = UIColor.black
    }
    
}
