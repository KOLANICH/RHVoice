/* Copyright (C) 2012  Olga Yakovleva <yakovleva.o.v@gmail.com> */

/* This program is free software: you can redistribute it and/or modify */
/* it under the terms of the GNU Lesser General Public License as published by */
/* the Free Software Foundation, either version 2.1 of the License, or */
/* (at your option) any later version. */

/* This program is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the */
/* GNU Lesser General Public License for more details. */

/* You should have received a copy of the GNU Lesser General Public License */
/* along with this program.  If not, see <https://www.gnu.org/licenses/>. */

#ifndef RHVOICE_SAPI_VOICE_TOKEN_HPP
#define RHVOICE_SAPI_VOICE_TOKEN_HPP

#include <comdef.h>
#include <comip.h>

#include "voice_attributes.hpp"
#include "ISpDataKeyImpl.hpp"

namespace RHVoice
{
  namespace sapi
  {
    class voice_token: public ISpDataKeyImpl
    {
    public:
      explicit voice_token(const voice_attributes& attr);

      STDMETHOD(OpenKey)(LPCWSTR pszSubKeyName,ISpDataKey **ppSubKey);
      STDMETHOD(EnumKeys)(ULONG Index,LPWSTR *ppszSubKeyName);

    private:
      bool str_equal(const std::wstring& s1,const std::wstring& s2) const;

      typedef std::map<std::wstring,std::wstring,str_less> attribute_map;
      typedef _com_ptr_t <_com_IIID<ISpObjectTokenInit, &IID_ISpObjectTokenInit>> ISpObjectTokenInitPtr;


      attribute_map attributes;
    };
  }  
}
#endif
