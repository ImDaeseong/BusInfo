
import UIKit

class roundbackground : UIView {
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        self.backgroundColor = UIColor.roundbackgroundColor()
        self.layer.borderColor = UIColor.orange.cgColor
        self.layer.borderWidth = 0.0
        self.layer.cornerRadius = 4
    }
}

