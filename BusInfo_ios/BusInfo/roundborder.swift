import UIKit

class roundborder : UIView {
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        self.layer.borderColor = UIColor(red: CGFloat(255) / 255.0, green: CGFloat(153) / 255.0, blue: CGFloat(0) / 255.0, alpha: 0.5).cgColor
        self.layer.borderWidth = 2.0
    }
}
