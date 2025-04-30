package com.id25.backend;

import com.id25.backend.dto.*;

import java.io.*;
import java.security.*;
import java.util.*;

public interface DataImporter {

    List<SurveyDto> importData() throws GeneralSecurityException, IOException;
}
