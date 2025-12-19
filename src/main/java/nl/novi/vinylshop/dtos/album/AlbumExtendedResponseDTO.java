package nl.novi.vinylshop.dtos.album;

import nl.novi.vinylshop.dtos.stock.StockResponseDTO;
import java.util.List;

public class AlbumExtendedResponseDTO extends AlbumResponseDTO {
    private List<StockResponseDTO> stockItems;

    public List<StockResponseDTO> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockResponseDTO> stockItems) {
        this.stockItems = stockItems;
    }
}
