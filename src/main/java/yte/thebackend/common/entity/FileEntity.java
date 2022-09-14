package yte.thebackend.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "file", schema = "public")
public class FileEntity extends BaseEntity {

    private String name;
    private String type;

    @Lob
    private byte[] data;

    public FileEntity(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

}
