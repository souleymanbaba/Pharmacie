package com.example.stage.stage.entity;

import com.example.stage.stage.dto.medicamentDto;
import com.example.stage.stage.enums.Forme;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "medicaments")
public class Medicaments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private String dosage;
    private String dosage_ar;

    private String name_ar;
    private String description_ar;
    private Forme forme;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;





    private int quantiteStock;

    @OneToMany(mappedBy = "medicaments", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MouvementStock> mouvementsStock;

    public medicamentDto getDto(String lang) {
        medicamentDto medicamentDto = new medicamentDto();
        medicamentDto.setId(id);

        // Nom du produit
        if ("ar".equals(lang) && name_ar != null) {
            medicamentDto.setName(name_ar);
        } else {
            medicamentDto.setName(name);
        }

        if ("ar".equals(lang) && dosage_ar != null) {
            medicamentDto.setDosage(dosage_ar);
        } else {
            medicamentDto.setName(dosage);
        }

        medicamentDto.setPrice(price);
        medicamentDto.setForme(forme);
        // Description du produit
        if ("ar".equals(lang) && description_ar != null) {
            medicamentDto.setDescription(description_ar);
        } else {
            medicamentDto.setDescription(description);
        }

        medicamentDto.setByteimg(img);




        medicamentDto.setQuantiteStock(quantiteStock);

        return medicamentDto;
    }

    public void updateStockQuantity(int quantity, MouvementStock.TypeMouvement type) {
        if (type == MouvementStock.TypeMouvement.ENTREE) {
            this.quantiteStock += quantity;
        } else if (type == MouvementStock.TypeMouvement.SORTIE) {
            if (this.quantiteStock >= quantity) {
                this.quantiteStock -= quantity;
            } else {
                throw new IllegalArgumentException("Not enough stock available");
            }
        }
    }
}
