package br.com.loto.service.impl;

import br.com.loto.domain.repository.IAccountPhotoRepository;
import br.com.loto.service.IAccountPhotoService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class AccountPhotoServiceImpl implements IAccountPhotoService {

    private final IAccountPhotoRepository photoRepository;
}
